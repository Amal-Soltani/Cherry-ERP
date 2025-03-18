importScripts('https://www.gstatic.com/firebasejs/8.0.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.0.0/firebase-messaging.js');

firebase.initializeApp({
  apiKey: "AIzaSyCXYF9PGu8daYgwagrzfbM7FWcqUPBgf_w",
  authDomain: "cherry-erp-be70e.firebaseapp.com",
  projectId: "cherry-erp-be70e",
  storageBucket: "cherry-erp-be70e.appspot.com",
  messagingSenderId: "321424034765",
  appId: "1:321424034765:web:9221386aba67e6545fd284",
  measurementId: "G-19G3VBQSLP",
  databaseURL: "https://angular-phone-authentication.firebaseio.com",
});

const messaging = firebase.messaging();

// Add additional Event Listners like Below:
self.addEventListener('notificationclick', (event) => {
  console.log('notification clicked!')
});
messaging.setBackgroundMessageHandler(function(payload) {
  const title = 'Hello World from SW!';
  const options = {
    body: payload.data.status
  };
  return self.registration.showNotification(title, options);
});
