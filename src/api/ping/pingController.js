module.exports = PingController();

function PingController() {
    var controller = {
        ping: ping
    };

    return controller;

    ////////////////////////////

    function ping(req, res) {
        res.status(200).json({status: 'OK'});
    }
}