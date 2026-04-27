const express = require('express');
const router = express.Router();
const { registerUser, loginUser } = require('../controllers/userController');
const { googleLogin } = require('../controllers/googleAuthController');

// POST /api/users/register
router.post('/register', registerUser);

// POST /api/users/login
router.post('/login', loginUser);

// POST /api/users/google-login
router.post('/google-login', googleLogin);

module.exports = router;

module.exports = router;
