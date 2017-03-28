var sequelize = require('../util/sequelizeInstance.js');

module.exports = Models();

function Models() {
    var registry = Registry();

    //Hooks
    registry.ShoppingCenter.beforeUpdate = incrementVersion;
    registry.ShoppingCenterAccess.beforeUpdate = incrementVersion;
    registry.Site.beforeUpdate = incrementVersion;
    registry.Store.beforeUpdate = incrementVersion;

    //Relationships
    registry.ShoppingCenter.hasMany(registry.ShoppingCenterAccess, {as: 'ShoppingCenterAccesses', foreignKey: 'shoppingCenterId'});
    registry.ShoppingCenter.hasMany(registry.Site, {as: 'Sites', foreignKey: 'shoppingCenterId'});

    registry.ShoppingCenterAccess.belongsTo(registry.ShoppingCenter);

    registry.Site.belongsTo(registry.ShoppingCenter);
    registry.Site.hasMany(registry.Store, {as: 'Stores', foreignKey: 'siteId'});

    registry.Store.belongsTo(registry.Site);

    return registry;
}

function Registry() {
    return {
        ShoppingCenter: sequelize.import(__dirname + '/shoppingCenter'),
        ShoppingCenterAccess: sequelize.import(__dirname + '/shoppingCenterAccess'),
        Site: sequelize.import(__dirname + '/site'),
        Store: sequelize.import(__dirname + '/store')
    };
}

function incrementVersion(instance, options) {
    instance.version++;
}