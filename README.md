# Spring Boot HATEOAS

## Gradle based spring boot application which provide Customer, Orders, Products APIs with HATEOAS links using test driven development.

## What is HATEOAS?
* HATEOAS stands for the Hypermedia(H), As(A), The(T), Engine(E), Of(O), Application(A), State(S). 
* If a consumer of a REST service needs to hard code all the resource URLs, then it is tightly coupled with your service implementation. Instead, if you return the URLs, it could use for the actions, then it is loosely coupled.  
* It is a way to provide links of the resources in the api response, so that the client does not have to deal with URI construction and business flow. 
* Simply clients make a request, the next URL is given by adding it to the response.

## Features of the Application
    - Read Customers
    - Read Customer by Customer Id
    - Read Products
    - Read Products by Product Id
    - Read Orders of a Customer
    - Read Order of a Customer by Order Id
    - Read Orders of a Product
    - Read Order of a Product by Order id

## Customer APIs

### Customers - Get Customers

* Request
```
GET /customers
Host: localhost:3000
```
* Response
```
Status code: 200 OK
Body:
{
    "_embedded": {
        "customerList": [
            {
                "id": 1,
                "name": "Ironman",
                "_links": {
                    "self": {
                        "href": "http://localhost:3000/customers/1"
                    },
                    "collection": {
                        "href": "http://localhost:3000/customers"
                    },
                    "orders": {
                        "href": "http://localhost:3000/customers/1/orders"
                    }
                }
            },
            {
                "id": 2,
                "name": "Thor",
                "_links": {
                    "self": {
                        "href": "http://localhost:3000/customers/2"
                    },
                    "collection": {
                        "href": "http://localhost:3000/customers"
                    },
                    "orders": {
                        "href": "http://localhost:3000/customers/2/orders"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:3000/customers"
        }
    }
}
```

### Customers - Get Customer by Customer Id

* Request
```
GET /customers/{1}
Host: localhost:3000
```
* Response
```
Status code: 200 OK
Body:
{
    "id": 1,
    "name": "Ironman",
    "_links": {
        "self": {
            "href": "http://localhost:3000/customers/1"
        },
        "collection": {
            "href": "http://localhost:3000/customers"
        },
        "orders": {
            "href": "http://localhost:3000/customers/1/orders"
        }
    }
}
```

## Product APIs

### Products - Get Products

* Request
```
GET /products
Host: localhost:3000
```
* Response
```
Status code: 200 OK
Body:
{
    "_embedded": {
        "productList": [
            {
                "id": 1,
                "name": "iPhone",
                "price": 80000.0,
                "_links": {
                    "self": {
                        "href": "http://localhost:3000/products/1"
                    },
                    "collection": {
                        "href": "http://localhost:3000/products"
                    },
                    "orders": {
                        "href": "http://localhost:3000/products/1/orders"
                    }
                }
            },
            {
                "id": 2,
                "name": "MacBook Pro",
                "price": 200000.0,
                "_links": {
                    "self": {
                        "href": "http://localhost:3000/products/2"
                    },
                    "collection": {
                        "href": "http://localhost:3000/products"
                    },
                    "orders": {
                        "href": "http://localhost:3000/products/2/orders"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:3000/products"
        }
    }
}
```

### Products - Get Product by Product Id

* Request
```
GET /products/{1}
Host: localhost:3000
```
* Response
```
Status code: 200 OK
Body:
{
    "id": 1,
    "name": "iPhone",
    "price": 80000.0,
    "_links": {
        "self": {
            "href": "http://localhost:3000/products/1"
        },
        "collection": {
            "href": "http://localhost:3000/products"
        },
        "orders": {
            "href": "http://localhost:3000/products/1/orders"
        }
    }
}
```

## Order APIs

### Orders - Get Orders of a Customer

* Request
```
GET /customers/{1}/orders
Host: localhost:3000
```
* Response
```
Status code: 200 OK
Body:
{
    "_embedded": {
        "orderList": [
            {
                "id": 1,
                "customer": {
                    "id": 1,
                    "name": "Ironman"
                },
                "product": {
                    "id": 1,
                    "name": "iPhone",
                    "price": 80000.0
                },
                "quantity": 2,
                "cost": 160000.0,
                "_links": {
                    "self": [
                        {
                            "href": "http://localhost:3000/customers/1/orders/1"
                        },
                        {
                            "href": "http://localhost:3000/products/1/orders/1"
                        }
                    ],
                    "customer": {
                        "href": "http://localhost:3000/customers/1/orders"
                    },
                    "product": {
                        "href": "http://localhost:3000/products/1/orders"
                    }
                }
            },
            {
                "id": 2,
                "customer": {
                    "id": 1,
                    "name": "Ironman"
                },
                "product": {
                    "id": 2,
                    "name": "MacBook Pro",
                    "price": 200000.0
                },
                "quantity": 1,
                "cost": 200000.0,
                "_links": {
                    "self": [
                        {
                            "href": "http://localhost:3000/customers/1/orders/2"
                        },
                        {
                            "href": "http://localhost:3000/products/2/orders/2"
                        }
                    ],
                    "customer": {
                        "href": "http://localhost:3000/customers/1/orders"
                    },
                    "product": {
                        "href": "http://localhost:3000/products/2/orders"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:3000/customers/1/orders"
        }
    }
}
```

### Orders - Get Order of a Customer by Order Id

* Request
```
GET /customers/{1}/orders/{2}
Host: localhost:3000
```
* Response
```
Status code: 200 OK
Body:
{
    "id": 2,
    "customer": {
        "id": 1,
        "name": "Ironman"
    },
    "product": {
        "id": 2,
        "name": "MacBook Pro",
        "price": 200000.0
    },
    "quantity": 1,
    "cost": 200000.0,
    "_links": {
        "self": [
            {
                "href": "http://localhost:3000/customers/1/orders/2"
            },
            {
                "href": "http://localhost:3000/products/2/orders/2"
            }
        ],
        "customer": {
            "href": "http://localhost:3000/customers/1/orders"
        },
        "product": {
            "href": "http://localhost:3000/products/2/orders"
        }
    }
}
```

### Orders - Get Orders of a Product

* Request
```
GET /products/{1}/orders
Host: localhost:3000
```
* Response
```
Status code: 200 OK
Body:
{
    "_embedded": {
        "orderList": [
            {
                "id": 1,
                "customer": {
                    "id": 1,
                    "name": "Ironman"
                },
                "product": {
                    "id": 1,
                    "name": "iPhone",
                    "price": 80000.0
                },
                "quantity": 2,
                "cost": 160000.0,
                "_links": {
                    "self": [
                        {
                            "href": "http://localhost:3000/customers/1/orders/1"
                        },
                        {
                            "href": "http://localhost:3000/products/1/orders/1"
                        }
                    ],
                    "customer": {
                        "href": "http://localhost:3000/customers/1/orders"
                    },
                    "product": {
                        "href": "http://localhost:3000/products/1/orders"
                    }
                }
            },
            {
                "id": 3,
                "customer": {
                    "id": 2,
                    "name": "Thor"
                },
                "product": {
                    "id": 1,
                    "name": "iPhone",
                    "price": 80000.0
                },
                "quantity": 1,
                "cost": 80000.0,
                "_links": {
                    "self": [
                        {
                            "href": "http://localhost:3000/customers/2/orders/3"
                        },
                        {
                            "href": "http://localhost:3000/products/1/orders/3"
                        }
                    ],
                    "customer": {
                        "href": "http://localhost:3000/customers/2/orders"
                    },
                    "product": {
                        "href": "http://localhost:3000/products/1/orders"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:3000/products/1/orders"
        }
    }
}
```

### Orders - Get Order of a Product by Order Id

* Request
```
GET /products/{1}/orders/{3}
Host: localhost:3000
```
* Response
```
Status code: 200 OK
Body:
{
    "id": 3,
    "customer": {
        "id": 2,
        "name": "Thor"
    },
    "product": {
        "id": 1,
        "name": "iPhone",
        "price": 80000.0
    },
    "quantity": 1,
    "cost": 80000.0,
    "_links": {
        "self": [
            {
                "href": "http://localhost:3000/customers/2/orders/3"
            },
            {
                "href": "http://localhost:3000/products/1/orders/3"
            }
        ],
        "customer": {
            "href": "http://localhost:3000/customers/2/orders"
        },
        "product": {
            "href": "http://localhost:3000/products/1/orders"
        }
    }
}
```
