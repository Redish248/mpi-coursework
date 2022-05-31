export interface ShipProfile {
  uid: number
  name: string
  surname: string
  email: string
  phone: string
  isPirate: boolean

  ship: Ship
}

export interface Ship {
  title: string
  speed: number
  capacity: number
  photo: string
  description: string
  rates: number
}
