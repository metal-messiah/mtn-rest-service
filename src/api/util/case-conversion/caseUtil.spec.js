var changeCase = require('change-case');

var CaseUtil = require('./caseUtil.js');

describe('caseConversionScaffolding', function() {

    it('should convert all object keys', function() {
        var source = {
            test_field: 'testField',
            test_1: 'test1'
        };

        var converted = CaseUtil.convert(source, changeCase.camelCase);

        expect(converted.testField).toEqual(source.test_field);
        expect(converted.test1).toEqual(source.test_1);
    });

    it('should convert objects in array', function() {
        var source = [
            {
                testField: 'testField',
                test1: 'test_1'
            }
        ];

        var converted = CaseUtil.convert(source, changeCase.snakeCase);

        expect(converted instanceof Array).toBe(true);
        expect(converted.test_field).toEqual(source.testField);
        expect(converted.test_1).toEqual(source.test1);
    });

    it('should convert an array within an array', function() {
        var source = [[
            {
                testField: 'testField',
                test1: 'test_1'
            }
        ]];

        var converted = CaseUtil.convert(source, changeCase.snakeCase);

        expect(converted instanceof Array).toBe(true);
        expect(converted[0] instanceof Array).toBe(true);
        expect(converted[0].test_field).toEqual(source[0].testField);
        expect(converted[0].test_1).toEqual(source[0].test1);
    });

    it('should skip non-objects in array', function() {
        var source = [
            'test',
            1,
            undefined
        ];

        var converted = CaseUtil.convert(source, changeCase.camelCase);

        expect(converted.length).toBe(3);
        expect(converted[0]).toBe('test');
        expect(converted[1]).toBe(1);
        expect(converted[2]).toBe(undefined);
    });
});