var sequelize = require('../util/sequelizeInstance.js');

module.exports = Models();

function Models() {
    var registry = Registry();

    //Hooks
    registry.ShoppingCenter.beforeUpdate = incrementVersion;
    registry.ShoppingCenterAccess.beforeUpdate = incrementVersion;

    //Relationships
    registry.ShoppingCenterAccess.hasOne(registry.ShoppingCenter);
    registry.ShoppingCenter.hasMany(registry.ShoppingCenterAccess, {as: 'Accesses'});

    return registry;
}

function Registry() {
    return {
        ShoppingCenter: sequelize.import(__dirname + '/shoppingCenter'),
        ShoppingCenterAccess: sequelize.import(__dirname + '/shoppingCenterAccess')
    };
}

function incrementVersion(instance, options) {
    instance.version++;
}