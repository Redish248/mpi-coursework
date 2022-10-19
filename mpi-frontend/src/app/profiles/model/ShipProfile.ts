export interface ShipProfile {
    uid: number
    name: string
    surname: string
    email: string
    phone: string
    isPirate: boolean
    afraid: boolean

    ship: Ship
}

export interface Ship {
    title: string
    speed: number
    capacity: number
    fuelConsumption: number
    length: number
    width: number
    pricePerDay: number
    photo: string
    description: string
    rates: number
}
