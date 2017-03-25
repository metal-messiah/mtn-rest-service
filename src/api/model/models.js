var sequelize = require('../util/sequelizeInstance.js');

module.exports = Models();

function Models() {
    var registry = Registry();

    //Hooks
    registry.ShoppingCenter.beforeUpdate = function(instance, options) {
        instance.version++;
    };

    //Relationships
    //registry.This.belongsTo(registry.That);

    return registry;
}

function Registry() {
    return {
        ShoppingCenter: sequelize.import(__dirname + '/shoppingCenter')
    };
}