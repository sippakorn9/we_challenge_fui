# Get a review by specific ID

Get the content of the review by review ID.

**URL** : `/reviews/:id`

**Method** : `GET`

## Success Response

**Code** : `200 OK`

**Response Body**
|  | Data Type  | Description  | 
|---|---|---|
| id | Integer | Review ID |
| reviewContent | String | Content of the review |

```json
{
    "id": 1234,
    "reviewContent": "review_content"
}
```

## Error Response

**Code** : `404 NOT FOUND`
For a user with ID 4321 on the local database but no details have been set yet.

```json
{
    "code" : 404, 
    "message" : "review not found."
}
```
