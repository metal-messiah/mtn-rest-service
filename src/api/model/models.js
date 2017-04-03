var sequelize = require('../util/sequelizeInstance.js');

module.exports = Models();

function Models() {
    var registry = Registry();

    defineHooks();
    defineRelationships();

    return registry;

    ////////////////////////////////

    function defineHooks() {
        registry.ShoppingCenter.beforeUpdate = incrementVersion;
        registry.ShoppingCenterAccess.beforeUpdate = incrementVersion;
        registry.Site.beforeUpdate = incrementVersion;
        registry.Store.beforeUpdate = incrementVersion;
    }

    function defineRelationships() {
        registry.ShoppingCenter.hasMany(registry.ShoppingCenterAccess, {as: 'ShoppingCenterAccesses', foreignKey: 'shoppingCenterId'});
        registry.ShoppingCenter.hasMany(registry.Site, {as: 'Sites', foreignKey: 'shoppingCenterId'});

        registry.ShoppingCenterAccess.belongsTo(registry.ShoppingCenter);

        registry.Site.belongsTo(registry.ShoppingCenter);
        registry.Site.hasMany(registry.Store, {as: 'Stores', foreignKey: 'siteId'});

        registry.Store.belongsTo(registry.Site);

        registry.UserIdentity.belongsTo(registry.UserProfile);

        registry.UserProfile.hasMany(registry.UserIdentity, {as: 'Identities', foreignKey: 'userProfileId'});
        registry.UserProfile.hasOne(registry.UserProfile, {as: 'CreatedBy', foreignKey: 'createdBy'});
        registry.UserProfile.hasOne(registry.UserProfile, {as: 'UpdatedBy', foreignKey: 'updatedBy'});
        registry.UserProfile.hasOne(registry.UserProfile, {as: 'DeletedBy', foreignKey: 'deletedBy'});
    }
}

function Registry() {
    return {
        ShoppingCenter: sequelize.import(__dirname + '/shoppingCenter'),
        ShoppingCenterAccess: sequelize.import(__dirname + '/shoppingCenterAccess'),
        Site: sequelize.import(__dirname + '/site'),
        Store: sequelize.import(__dirname + '/store'),
        UserIdentity: sequelize.import(__dirname + '/userIdentity'),
        UserProfile: sequelize.import(__dirname + '/userProfile')
    };
}

function incrementVersion(instance, options) {
    instance.version++;
}