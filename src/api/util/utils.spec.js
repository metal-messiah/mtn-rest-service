var Utils = require('./utils.js');

describe('Utils', function() {

    describe('cleanUndefined', function() {

        it('should not throw an error if given a null or undefined value', function() {
            expect(function() {
                Utils.cleanUndefined(undefined);
            }).not.toThrow();
            expect(function() {
                Utils.cleanUndefined(null);
            }).not.toThrow();
        });

        it('should remove undefined values', function() {
            var object = {
                test: undefined
            };

            expect(object.hasOwnProperty('test')).toBe(true);
            Utils.cleanUndefined(object);
            expect(object.hasOwnProperty('test')).toBe(false);
        });

        it('should work recursively', function() {
            var object = {
                undefinedProperty: undefined,
                childObject: {
                    definedProperty: 'test',
                    undefinedProperty: undefined
                }
            };

            Utils.cleanUndefined(object);

            expect(object.hasOwnProperty('undefinedProperty')).toBe(false);
            expect(object.childObject.hasOwnProperty('undefinedProperty')).toBe(false);
            expect(object.childObject.hasOwnProperty('definedProperty')).toBe(true);
        });

        it('should not remove defined values, including null and falsy values', function() {
            var object = {
                nullValue: null,
                falseValue: false,
                zeroValue: 0,
                somethingElse: 'true!'
            };

            Utils.cleanUndefined(object);

            expect(object.nullValue).toBe(null);
            expect(object.falseValue).toBe(false);
            expect(object.zeroValue).toBe(0);
            expect(object.somethingElse).toBe('true!');
        });
    });

    describe('isNumeric', function() {

        it('should return true for numeric values', function() {
            var values = [
                0,
                1.0,
                0.1,
                -1.2,
                Math.PI,
                '0',
                '1.0',
                '0.1',
                '-1.2'
            ];

            for (var i = 0; i < values.length; i++) {
                expect(Utils.isNumeric(values[i])).toBe(true);
            }
        });

        it('should return false for non-numeric values', function() {
            var values = [
                true,
                false,
                null,
                undefined,
                'string',
                'abc112',
                '123a',
                [],
                {}
            ];

            for (var i = 0; i < values.length; i++) {
                expect(Utils.isNumeric(values[i])).toBe(false);
            }
        });
    });

    describe('isPositiveInteger', function() {

        it('should return true for positive integer values', function() {
            var values = [
                1,
                '1',
                '1234567890',
                Number.MAX_VALUE
            ];

            for (var i = 0; i < values.length; i++) {
                expect(Utils.isPositiveInteger(values[i])).toBe(true);
            }
        });

        it('should return false for negative integer or non-numeric values', function() {
            var values = [
                true,
                false,
                null,
                undefined,
                'string',
                'abc112',
                '123a',
                [],
                {},
                -1,
                '-1'
            ];

            for (var i = 0; i < values.length; i++) {
                expect(Utils.isPositiveInteger(values[i])).toBe(false);
            }
        });
    });
});