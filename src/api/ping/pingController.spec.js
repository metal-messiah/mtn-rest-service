var Mock = require('node-mocks-http');

var PingController = require('./pingController.js');

describe('PingController', function () {

    describe('ping', function () {

        var req, resp;

        beforeEach(function () {
            req = Mock.createRequest({
                method: 'GET',
                url: '/ping'
            });

            resp = Mock.createResponse();
        });

        it('should return a 200 status', function () {
            PingController.ping(req, resp);
            expect(resp.statusCode).toBe(200);
        });

        it('should return a simple JSON body', function () {
            PingController.ping(req, resp);

            var body = JSON.parse(resp._getData());
            expect(body).toEqual({status: 'OK'});
        });
    });
});