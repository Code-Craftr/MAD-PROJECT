const express = require('express');
const router = express.Router();
const { getAllOutlets, getOutletMenu } = require('../controllers/outletController');

router.get('/', getAllOutlets);
router.get('/:id/menu', getOutletMenu);

module.exports = router;
