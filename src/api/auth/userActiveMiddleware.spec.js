var Mock = require('node-mocks-http');

var User = require('./user.js');
var UserActiveMiddleware = require('./userActiveMiddleware.js');

describe('UserActiveMiddleware', function() {

    var req, resp, nextWrapper;

    beforeEach(function() {
        req = Mock.createRequest({
            method: 'GET',
            url: '/'
        });
        resp = Mock.createResponse();
        nextWrapper = {
            next: function() {}
        };
    });

    it('should return 500 if no user found on request', function() {
        UserActiveMiddleware(req, resp, nextWrapper.next);
        expect(resp.statusCode).toBe(500);
    });

    it('should return 401 if user is not active', function() {
        req.mtnUser = new User(1, 'test');
        UserActiveMiddleware(req, resp, nextWrapper.next);
        expect(resp.statusCode).toBe(401);
    });

    it('should call next if user is active', function() {
        spyOn(nextWrapper, 'next');

        req.mtnUser = new User(1, 'test', {isActive: true});
        UserActiveMiddleware(req, resp, nextWrapper.next);
        expect(nextWrapper.next).toHaveBeenCalled();
    });
});