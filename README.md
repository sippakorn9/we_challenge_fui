# we_challenge_fui
Repository for Wongnai WeChallenge

Run by docker-compose -f docker-compose.yml up -d
Client Side Application can access by port 5555
Server Side Application can access by port 8080

### API Docs

* [Get review by id](apidocs/get_specific.md) : `GET /reviews/:id`
* [Search reviews by query](apidocs/search_reviews.md) : `GET /reviews?query=:text`
* [Edit review by id](apidocs/editting_review.md) : `PUT /reviews/:id`
