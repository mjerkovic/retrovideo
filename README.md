Really basic Contact application

For testing different technologies.

Access the app via http://localhost:8080/video.html

curl -v -X POST -d '{ "videoId": "123", "title": "Cujo" }' -H 'Content-Type: application/json' http://localhost:8080/perform/addVideo