export interface CrewProfile {
  // user info
  uid: number
  name: string
  surname: string
  email: string
  phone: string
  is_pirate: string

  crew: Crew

}

export interface Crew {
  uid: number,
  teamName: string,
  rates: number
  photo: string
  description: string
  members: CrewMember[]
  membersNumber: number
  // tripNumber: number TODO
}

export interface CrewMember {
  uid: number
  fullName: string
  experience: number
}