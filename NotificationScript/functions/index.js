const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

exports.createNotificationToTopic = functions.firestore.document('Events/{uid}').onCreate((snap,con) =>{
    let snapshot = snap.data();
    let title = snapshot.eventName;
    let content = snapshot.eventDescription;
    var mess = {
        notification :{
            title : title,
            body : content,
        }
    };
    
    return admin.messaging().sendToTopic("eventupdated",mess);
    
});

// exports.checkflag = functions.database.ref('/flagparent') 
// .onUpdate((snapshot, context) => {
// const temptoken = 'yourapptoken';  
// // const flag = snapshot.before.val();   TO GET THE OLD VALUE BEFORE UPDATE
// const flag = snapshot.after.val();
// let statusMessage = `Message from the clouds as ${flag}`
// var message = {
// notification: {
// title: 'cfunction',
// body: statusMessage
// },
// token: temptoken
// };
// admin.messaging().send(message).then((response) => {
// console.log("Message sent successfully:", response);
// return response;
// })
// .catch((error) => {
// console.log("Error sending message: ", error);
// });
// });