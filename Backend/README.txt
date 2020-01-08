This file contains the list of active query commands and their corresponding inputs, formats, and outputs

Request - ""
	Used as a main welcoming screen
	Type - GetMapping
		Return - string

Request - "/user"
	Used as a user welcoming screen
	Type - GetMapping
		Return - string
Request - "/user/login"
	Used to generate a token for a user
	Type - GetMapping
		Input - Username and password of User logging in
			inputs: 
				User
			JSON format :
			{
				"username" : "string",
				"password" : "string"
			}
		Return - Token (used for future communication while logged in) if user exists, otherwise null
Request - "/user/get"
	Used to get all users in the database
	Type - GetMapping
	    Input - Authenticator(key) and expiration time of a token of an admin
			inputs:
				Token
		    JSON format:
		    {
		        "authenticator": "string",
				"expiration": long
		    }
		Return - List<User> (containing list of all users in database) if admin token is valid, otherwise null
Request - "/user/get/user"
	Used to get specific users' from the database
	Type - GetMapping
		Input - Authenticator (key) and expiaration time of a token for the user desired
		    inputs:
		        Token
		    JSON format:
		    {
		        "authenticator": "string",
				"expiration": long
		    }
		Return - User if exists, otherwise null
Request - "/user/get/location"
	Used to get the Location data of a User
	Type - GetMapping
		Input - Authenticator(key) and expiration time of a token for the user desired
		    inputs:
		        Token
		    JSON format:
		    {
		        "authenticator": "string",
				"expiration": long
		    }
		Return - Location of User if exists, otherwise null
Request - "/user/add"
	Used to add a user to the database
	Type - PostMapping
		Input - Username and password of user adding
			inputs:
				User
			JSON format:
			{
				"username": "string",
				"password": "string"
			}
		Return - True if adding was successful, false otherwise
Request - "/user/update"
	Used to update the contents of a user
	Type - PutMapping
		Input - UserUpdateWrapper which contains the token of the user to update and the user object updating to.
				In cases of elevating user's authLevel, an admin token needs to be provided
			inputs:
				toUpdateToken - Token of user to update
				updateTo - user containing data to update u1 with
				adminToken - Token of admin to validate updating users to authLevels > 0
							 only needed if updating users to authLevels > 0
			JSON format:
			{
				"toUpdateToken": {
					"authenticator": "string",
					"expiration": long
				},
				"updateTo": {
					//any one of these can be provided. E.G. if you are updating only the username, only the username section is needed.
					
					"username": "string",
					"password": "string",
					"authLevel": "int"
				},
				"u3": {
					"authenticator": "string",
					"expiration": long
				}
			}
		Return - True if updating was successful, false otherwise
Request - "/user/delete"
	Used to delete a user from the database
	Type - DeleteMapping
		Input - Authenticator(key) and expiration time of a token for the user desired
			inputs:
				Token
			JSON format:
			{
				"authenticator": "string",
				"expiration": long
			}
		Return - true if deletion was successful, false otherwise
Request - "/user/logout"
	Used to reset a user's token to null to stop communication
	Type - DeleteMapping
		Input - Authenticator(key) and expiration time of a token for the user desired
			inputs:
				Token
			JSON format:
			{
				"authenticator": "string",
				"expiration": long
			}
		Return - true if token was reset to null, false otherwise

Request - "/item"
	Used as an item welcoming screen
	Type - GetMapping
		Return - string
Request - "/item/get"
	Used to get all items in the database
	Type - GetMapping
		Return - List<ItemsAbs> (containing list of all items in database)
Request - "/item/get/{id}"
	Used to get an item from the database
	Type - GetMapping
		Input - int (id of the item)
		Return - Optional<ItemAbs> (item with id entered if exists)

Request - "/server"
	Used as a server welcoming screen
	Type - GetMapping
		Return - string	
Administrators in database:
    {
        "username": "sean"
        "password": "adminSean123"
        "authLevel": "2"
        "id": "1"
    }
    {
        "username": "caden"
        "password": "adminCaden123"
        "authLevel": "2"
        "id": "2"
    }
    {
        "username": "jason"
        "password": "adminJason123"
        "authLevel": "2"
        "id": "3"
    }
    {
        "username": "jeff"
        "password": "adminJeff123"
        "authLevel": "2"
        "id": "4"
    }
