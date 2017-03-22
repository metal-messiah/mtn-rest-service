var Mock = require('node-mocks-http');
var Errors = require('../error/errors.js');

var ResponseUtil = require('./responseUtil.js');

describe('ResponseUtil', function() {

    var res;

    beforeEach(function() {
        res = Mock.createResponse();
    });

    describe('error', function() {

        it('should return 500 and "An Unexpected Error Occurred" message by default', function() {
            ResponseUtil.error(res);
            expect(res.statusCode).toBe(500);

            var body = JSON.parse(res._getData());
            expect(body).toEqual({message: 'An Unexpected Error Occurred'});
        });

        it('should return default values for generic Error', function() {
            ResponseUtil.error(res, new Error('Something went wrong'));
            expect(res.statusCode).toBe(500);

            var body = JSON.parse(res._getData());
            expect(body).toEqual({message: 'An Unexpected Error Occurred'});
        });

        it('should return configured status for custom Error', function() {
            var error = new Errors.UnauthorizedError();
            ResponseUtil.error(res, error);

            expect(res.statusCode).toBe(error.status);
            var body = JSON.parse(res._getData());
            expect(body).toEqual({message: error.message});
        });
    });

    describe('ok', function() {

        it('should return a 200 when no data provided', function() {
            ResponseUtil.ok(res);
            expect(res.statusCode).toBe(200);
        });

        it('should return a 200 and provided data as JSON body', function() {
            ResponseUtil.ok(res, {test:'test'});
            expect(res.statusCode).toBe(200);

            var body = JSON.parse(res._getData());
            expect(body).toEqual({test: 'test'});
        });
    });
});