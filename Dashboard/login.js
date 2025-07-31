import { initializeApp } from "https://www.gstatic.com/firebasejs/11.6.0/firebase-app.js";
        import { getFirestore, collection, getDocs, query, where } from "https://www.gstatic.com/firebasejs/11.6.0/firebase-firestore.js";
      
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
        const db = getFirestore(app);
      
        // Wait until the DOM is ready
        document.addEventListener('DOMContentLoaded', () => {
          const loginBtn = document.getElementById('loginButton');
          
          loginBtn.addEventListener('click', async () => {
            const emailInput = document.getElementById('email').value;
            const passwordInput = document.getElementById('password').value;
      
            try {
              // Query Firestore for matching admin
              const adminRef = collection(db, 'Admin');
              const q = query(adminRef, where('email', '==', emailInput), where('password', '==', passwordInput));
              const querySnapshot = await getDocs(q);
      
              if (!querySnapshot.empty) {
                // Found matching admin
                const adminDoc = querySnapshot.docs[0];
                const adminData = adminDoc.data();
      
                if (adminData.type === "admin") {
                  localStorage.setItem('isAdminLoggedIn', 'true');
                  localStorage.setItem('adminEmail', emailInput);
                  window.location.href = 'dashboard.html';
                } else {
                  alert("Not authorized. Only admins can log in.");
                }
              } else {
                alert("Invalid email or password.");
              }
      
            } catch (error) {
              console.error("Error during login:", error.message);
              alert("Login failed. Please try again.");
            }
          });
        });