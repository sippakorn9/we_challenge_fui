# Get a review by specific ID

Get the details of the currently Authenticated User along with basic
subscription information.

**URL** : `/reviews/:id`

**Method** : `GET`

## Success Response

**Code** : `200 OK`

**Request Body Examples**


```json
{
    "data": {
        "id": 1234,
        "review_content": "review_content",
    },
    "errors" : []
}
```

## Error Response

**Code** : `404 NOT FOUND`
For a user with ID 4321 on the local database but no details have been set yet.

```json
{
    "data" : {},
    "errors" : 
        [
            {"code" : 404, "message" : "review not found."}
        ]
}
```