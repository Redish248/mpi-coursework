export interface CrewProfile {
    // user info
    uid: number
    name: string
    surname: string
    email: string
    phone: string
    isPirate: boolean

    crew: Crew
}

export interface Crew {
    teamName: string,
    rates: number
    photo: string
    description: string
    members: CrewMember[]
    membersNumber: number
    pricePerDay: number
    // tripNumber: number TODO
}


export interface CrewMember {
    uid: number
    fullName: string
    experience: number
}
