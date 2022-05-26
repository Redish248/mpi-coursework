export interface Profile {
  // user info
  uid: number
  name: string
  surname: string
  email: string
  phone: string
  is_pirate: string

  // general object info (ship or crew)
  photo: string
  rates: number
  tripNumber: number
  description: string

  // one of two fields must be not null
  ship?: Ship
  crew?: Crew
}

export interface Ship {
  uid: number
  title: string
  speed: number
  capacity: number
}

export interface Crew {
  uid: number,
  teamName: string,
  members: CrewMember[]
  membersNumber: number
}

export interface CrewMember {
  uid: number
  fullName: string
  experience: number
}