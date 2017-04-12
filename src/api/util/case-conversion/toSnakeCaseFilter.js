var changeCase = require('change-case');

var CaseUtil = require('./caseUtil.js');

module.exports = ToSnakeCaseFilter;

function ToSnakeCaseFilter(req, res, next) {
    if (req.body) {
        req.body = CaseUtil.convert(req.body, changeCase.snakeCase);
    }
    next();
}