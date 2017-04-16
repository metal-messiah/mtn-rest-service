(function() {
    angular.module('mtn').factory('PaginatorFactory', PaginatorFactory);

    function PaginatorFactory($q) {
        var service = {
            create: create
        };

        ////////////////////////

        function create(pageOneContents, totalResults, limit) {
            return new Paginator(pageOneContents, totalResults, limit);
        }

        function Paginator(pageOneContents, totalResults, limit) {
            this.pageNumber = 0;
            this.totalResults = totalResults || 0;
            this.limit = limit || 10;
            this.queryParams = {};

            this.totalPages = Math.ceil(totalResults / limit);
            this.pages = [];

            //Add first page immediately
            this.pages.push(new Page(0, pageOneContents));

            //Create empty pages now to allow skipping to end
            for (var i = 1; i < this.totalPages; i++) {
                this.pages.push(new Page(i));
            }
        }

        Paginator.prototype.setQueryFunction = function(queryFunction) {
            this.queryFunction = queryFunction;
            return this;
        };

        Paginator.prototype.setQueryParams = function(queryParams) {
            if (queryParams) {
                this.queryParams = queryParams;
            }
            return this;
        };

        Paginator.prototype.getPreviousPageOffset = function() {
            return (this.pageNumber - 1) * this.limit;
        };

        Paginator.prototype.getNextPageOffset = function() {
            return (this.pageNumber + 1) * this.limit;
        };

        Paginator.prototype.getLastPageOffset = function() {
            return this.totalPages * this.limit;
        };

        Paginator.prototype.getCurrentPage = function() {
            return this.pages[this.pageNumber];
        };

        Paginator.prototype.canPrevious = function() {
            return this.pageNumber < 0;
        };

        Paginator.prototype.canNext = function() {
            return this.pageNumber < this.totalPages + 1;
        };

        Paginator.prototype.next = function() {
            var deferred = $q.defer();

            //If we already have the next page contents, simply increment the page number
            if (this.pages[this.pageNumber + 1].content) {
                this.pageNumber++;
                deferred.resolve();
            }
            //Else, we need to load the next page contents before incrementing
            else {
                var _this = this;
                this.queryParams.limit = this.limit;
                this.queryParams.offset = this.getNextPageOffset();
                this.queryFunction(this.queryParams).then(
                    function(results) {
                        _this.pages[_this.pageNumber + 1].content = results.content;
                        _this.pageNumber++;
                        deferred.resolve();
                    },
                    function(response) {
                        deferred.reject(response);
                    }
                )
            }

            return deferred.promise;
        };

        Paginator.prototype.previous = function() {
            var deferred = $q.defer();

            //If we already have the previous page contents, simply decrement the page number
            if (this.pages[this.pageNumber - 1].content) {
                this.pageNumber--;
                deferred.resolve();
            }
            //Else, we need to load the previous page contents before decrementing
            else {
                var _this = this;
                this.queryParams.limit = this.limit;
                this.queryParams.offset = this.getPreviousPageOffset();
                this.queryFunction(this.queryParams).then(
                    function(results) {
                        _this.pages[_this.pageNumber - 1].content = results.content;
                        _this.pageNumber--;
                        deferred.resolve();
                    },
                    function(response) {
                        deferred.reject(response);
                    }
                )
            }

            return deferred.promise;
        };

        Paginator.prototype.last = function() {
            var deferred = $q.defer();

            //If we already have the last page contents, simply set the page number
            if (this.pages[this.totalPages - 1].content) {
                this.pageNumber = this.totalPages;
                deferred.resolve();
            }
            //Else, we need to load the last page contents before setting
            else {
                var _this = this;
                this.queryParams.limit = this.limit;
                this.queryParams.offset = this.getLastPageOffset();
                this.queryFunction(this.queryParams).then(
                    function(results) {
                        _this.pages[_this.totalPages - 1].content = results.content;
                        _this.pageNumber = _this.totalPages;
                        deferred.resolve();
                    },
                    function(response) {
                        deferred.reject(response);
                    }
                )
            }

            return deferred.promise;
        };

        Paginator.prototype.first = function() {
            this.pageNumber = 0;

            //Return empty promise for consistency with other navigation functions
            var deferred = $q.defer();
            deferred.resolve();
            return deferred.promise;
        };

        Paginator.prototype.currentPageRange = function() {
            var startValue = this.pageNumber * this.limit + 1;
            var endValue = this.pageNumber * this.limit + this.limit;
            if( endValue > this.totalResults ) {
                endValue = this.totalResults;
            }
            return startValue + '-' + endValue;
        };

        function Page(number, content) {
            this.number = number;
            this.content = content;
        }

        ///////////////////////

        return service;
    }
})();