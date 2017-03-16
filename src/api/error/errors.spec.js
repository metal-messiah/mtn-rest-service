var Errors = require('./errors.js');

describe('Errors', function() {

    /**
     * These tests exist to ensure that the dynamic creation of Error subclasses
     * works properly.
     *
     * IT IS NOT NECESSARY TO PROVIDE THESE TESTS FOR EVERY CUSTOM ERROR TYPE!
     */
    describe('UnauthorizedError', function() {

        it('should be an instance of Error', function() {
            var error = new Errors.UnauthorizedError();
            expect(error instanceof Error).toBe(true);
        });

        it('should be an instance of UnauthorizedError', function() {
            var error = new Errors.UnauthorizedError();
            expect(error instanceof Errors.UnauthorizedError).toBe(true);
        });

        it('should set message', function() {
            var error = new Errors.UnauthorizedError('No permission');
            expect(error.message).toBe('No permission');
        });

        it('should set extra', function() {
            var error = new Errors.UnauthorizedError('No Permission', 100);
            expect(error.extra).toBe(100);
        });
    });
});