module.exports = {
    ping: ping
};

////////////////////////////

function ping(req, res) {
    res.status(200).json({status: 'OK'});
}