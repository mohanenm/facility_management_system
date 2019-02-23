# Facility Management Use Cases

## Facility Use Cases

### add new facility

User requests new facility providing a name only

### add facility detail

User specifies the facility name, buildings associated, and rooms associated

The facility detail must be a json object that looks similar to this example: 

```json

{
    maintenance_rate: 16,
    buildings: [
        {
            name: "b1",
            rooms: [
                {
                    room_num: 101,
                    capacity: 10
                },
                {
                    room_num: 102,
                    capacity: 10
                }
            ]
        },
         {
            name: "b2",
            rooms: [
                {
                    room_num: 101,
                    capacity: 10
                },
                {
                    room_num: 102,
                    capacity: 10
                }
            ]
        }
    ]
}


| attribute | description |
|---| -- | 
| maintenance_rate | Hourly rate for maintenance anywhere in facility



### list facilities



