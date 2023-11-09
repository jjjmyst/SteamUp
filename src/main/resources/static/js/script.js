    function toggleLogin() {
        // Simulate user authentication state
        const isLoggedIn = (document.getElementById('username').textContent !== 'Guest');

        if (isLoggedIn) {
            window.location.href = '/logout'; // Simulating logout by redirecting
        } else {
            // User is not logged in
            window.location.href = '/login'; // Redirect to the login page
        }
    }
