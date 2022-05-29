export interface ShipProfile {
  uid: number
  name: string
  surname: string
  email: string
  phone: string
  is_pirate: string

  ship: Ship
}

export interface Ship {
  uid: number
  title: string
  speed: number
  capacity: number
  photo: string
  description: string
}
