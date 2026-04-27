const express = require('express');
const router = express.Router();
const { placeOrder, getUserOrders, getOutletOrders, updateOrderStatus, getOrderById } = require('../controllers/orderController');

router.post('/', placeOrder);
router.get('/:orderId', getOrderById);
router.get('/user/:userId', getUserOrders);
router.get('/outlet/:outletId', getOutletOrders);
router.patch('/:orderId/status', updateOrderStatus);

module.exports = router;
