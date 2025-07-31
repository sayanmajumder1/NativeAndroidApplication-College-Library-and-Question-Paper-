import { initializeApp } from "https://www.gstatic.com/firebasejs/11.6.0/firebase-app.js";
import { getAuth, signOut } from "https://www.gstatic.com/firebasejs/11.6.0/firebase-auth.js";

// Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyAHBwPeUMLjtG_RP6tGDboM8jdcGjIXzi8",
  authDomain: "vison-b6c63.firebaseapp.com",
  databaseURL: "https://vison-b6c63-default-rtdb.firebaseio.com",
  projectId: "vison-b6c63",
  storageBucket: "vison-b6c63.appspot.com",
  messagingSenderId: "228751732398",
  appId: "1:228751732398:web:2aa95bc6693c6983d8873f",
  measurementId: "G-RT0Y0REFSR"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

// ✅ Handle logout
document.addEventListener('DOMContentLoaded', () => {
  const logoutButton = document.getElementById('logoutButton');
  if (logoutButton) {
    logoutButton.addEventListener('click', () => {
      signOut(auth)
        .then(() => {
          localStorage.removeItem('isAdminLoggedIn');
          localStorage.removeItem('adminEmail');
          window.location.href = 'login.html';
        })
        .catch((error) => {
          console.error("Error during logout: ", error);
        });
    });
  }
});

// ✅ Redirect if not logged in
window.onload = function () {
  const isAdminLoggedIn = localStorage.getItem('isAdminLoggedIn');
  const adminEmail = localStorage.getItem('adminEmail');

  if (isAdminLoggedIn !== 'true') {
    window.location.href = 'login.html';
  } else {
    const emailElement = document.getElementById('adminEmail');
    if (emailElement) {
      emailElement.textContent = adminEmail;
    }
  }
};
