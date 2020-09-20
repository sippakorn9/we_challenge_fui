# Search for reviews by a query (Food text)

Search all reviews that contain query text in the content with marked keyword

**URL** : `/reviews?query=:text`

**Method** : `GET`

## Success Responses

**Condition** : No review match the keyword.

**Code** : `200 OK`

**Response Body**
|  | Data Type  | Description  | 
|---|---|---|
| count | Integer | Number of reviews match the query |
| reviews | List | Content of the review |
| reviews.id | Integer | Review ID |
| reviews.reviewContent | String | Review Content with mark on requested keyword |

**Content** : 
```json
{
    "count" : 0,
    "reviews" : []
}
```

### OR

**Condition** : Reviews match the keyword.

**Code** : `200 OK`

**Content** : In this example, search with 'fried rice' keyword and found 2 reviews.

```json
{
    "count" : 2,
    "reviews" : [
        {
            "id": 1,
            "reviewContent": "review <keyword>fried rice</keyword>",
        },
        {
            "id": 2,
            "reviewContent": "<keyword>fried rice</keyword> review and another <keyword>fried rice</keyword>",
        }
    ]
}
```
