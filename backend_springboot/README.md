
# Twitter Spring Boot Backend

A spring boot backend for twitter clone apps that provides endpoints for basic operations like create account, sign in, post tweet,reply and like tweet etc.

App is secured by spring boot security and jwt authentication.


---

## Technologies Used

- Spring Boot
- Spring Security
- MySQL Database
- JWT (JSON Web Token)
- Spring Data JPA (Java Persistence API)
- Spring Data JDBC (Java Database Connectivity)
- Spring Web
- Lombok


## Getting Started

### Prerequisites

- Java 11 or higher
- Spring Boot 3+
- MySQL Database

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/firatkill/twitter_fullstack_app.git
   ```

2. Navigate to the fullstack project directory:

   ```bash
   cd twitter_fullstack_app
   ```
2. Navigate to the backend project directory:

   ```bash
   cd backend_springboot
   ```

3. Build the project:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

- Automatically Creates DB. Ready to use

## Configuration

- Database configurations can be found in `application.properties`.
- JWT secret key is set in `JWTService.java` under `com/x/twitter/Security/JWT/JWTService.java`.


## Contribution

Pull requests are welcome. 












# API Usage

#### Create Account

```http
  POST /api/auth/register
```

| Parameter | Type     | Explanation                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Required** Name of the account. |
| `username` | `string` | **Required** unique username   |
| `biography` | `string` | **Required**. biography of the account. |
| `password` | `string` | **Required** password. |
| `profile_photo` | `string` |  Profile photo for the account. |


#### Sign in

```http
  POST /api/auth/login
```
**Request :**

| Parameter | Type     | Explanation                |
| :-------- | :------- | :-------------------------------- |
| `username` | `string` | **Required** user's username   |
| `password` | `string` | **Required** user's password. |

**Successful Response :**
Returns 'user' object that has 2 parameters:

| Parameter | Type     | Explanation                |
| :-------- | :------- | :-------------------------------- |
| `username` | `string` | User's username   |
| `access_token` | `string` | JWT access token |


**Unsuccessful Response :**
Returns 'error' object that has 2 parameters:

| Parameter | Type     | Explanation                |
| :-------- | :------- | :-------------------------------- |
| `status` | `string` | Status code for error   |
| `detail` | `string` | Error detail |



#### Sign in

```http
  POST /api/auth/login
```
**Request :**

| Parameter | Type     | Explanation                |
| :-------- | :------- | :-------------------------------- |
| `username` | `string` | **Required** user's username   |
| `password` | `string` | **Required** user's password. |

**Successful Response :**
Returns 'user' object that has 2 parameters:

| Parameter | Type     | Explanation                |
| :-------- | :------- | :-------------------------------- |
| `username` | `string` | User's username   |
| `access_token` | `string` | JWT access token |


- For the first login, returns an user object with JWT access token. 
- For all other requests, in the Request Header, a jwt token must be provided with 'Authorization' parameter and all other requests must be sent with this header:


**In Request Headers:**

| Header | Value     | Explanation                |
| :-------- | :------- | :-------------------------------- |
| `Authorization` | `Bearer {token}` | **Required** JWT access token   |


---
# Tweet Endpoints

### Posting a tweet

```http
  POST /api/tweets
```

**Request Body:**

| Parameter    | Type     | Explanation                  |
| :----------- | :------- | :--------------------------- |
| `content`    | `string` | **Required** tweet content   |
| `reply_to`    | `long` | Replied tweet's id if this is a reply. if not, then not need to set. default is 0   |



---

### Get all tweets of the user thats logged in

```http
  GET /api/tweets
```
- No Response body

### Get one tweet

```http
  GET /api/tweets/{tweetId}
```

**Path Parameters:**

| Parameter  | Type     | Explanation          |
| :--------- | :------- | :------------------- |
| `tweetId`  | `long`   | **Required** tweet ID |


**Successful Response:**
Returns 'tweet' object that has 4 parameters:

| Parameter     | Type     | Explanation                   |
| :------------ | :------- | :---------------------------- |
| `id`          | `long`   | Tweet ID                      |
| `content`     | `string` | Tweet content                 |
| `reply_to`     | `string` | If tweet is a reply, replied tweet's id                 |
| `likeeCount`   | `int`    | Number of likes for the tweet |
| `userId`      | `long`   | ID of the user who posted the tweet |
| `username`     | `string` | username of the user who posted the tweet                 |
| `created_at`     | `Instant` | Posting date of tweet|

---

### Delete a Tweet

```http
  DELETE /api/tweets/{tweetId}
```

**Path Parameters:**

| Parameter  | Type     | Explanation          |
| :--------- | :------- | :------------------- |
| `tweetId`  | `long`   | **Required** tweet ID |

- No Response Body

---

### Like a Tweet 

```http
  POST /api/tweets/{tweetId}/add-like
```

**Path Parameters:**

| Parameter  | Type     | Explanation          |
| :--------- | :------- | :------------------- |
| `tweetId`  | `long`   | **Required** tweet ID |

- No response body

---

### Unlike a tweet

```http
  POST /api/tweets/{tweetId}/remove-like
```

**Path Parameters:**

| Parameter  | Type     | Explanation          |
| :--------- | :------- | :------------------- |
| `tweetId`  | `long`   | **Required** tweet ID |

---

#### User Profile and Follow Endpoints

### Get User Profile

```http
  GET /api/users/{username}
```

**Path Parameters:**

| Parameter  | Type     | Explanation             |
| :--------- | :------- | :---------------------- |
| `username` | `string` | **Required** username   |

**Successful Response:**
Returns 'userProfile' object that has 2 parameters:

| Parameter    | Type     | Explanation           |
| :----------- | :------- | :-------------------- |
| `name`   | `string` | Name of user account       |
| `username`   | `string` |  Unique username of account       |
| `profile_photo` | `byte[]` | User's profile photo |
| `biography`   | `string` | biography of the account|
| `created_at`   | `Instant` | Profile creation date|
| `follower_count`   | `long` | number of followers|
| `following_count`   | `long` | number of followings|
| `tweets`   | `Array of tweets` | Tweets of user|

---

### Follow a User

```http
  POST /api/users/{username}/follow
```

**Path Parameters:**

| Parameter  | Type     | Explanation             |
| :--------- | :------- | :---------------------- |
| `username` | `string` | **Required** username   |

---

### Unfollow a User

```http
  POST /api/users/{username}/unfollow
```

**Path Parameters:**

| Parameter  | Type     | Explanation             |
| :--------- | :------- | :---------------------- |
| `username` | `string` | **Required** username   |

---

