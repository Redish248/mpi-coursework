export interface User {
  nickname: string
}

export enum Permissions {
  TRAVELER = "TRAVELER",
  CREW_MANAGER = "CREW_MANAGER",
  SHIP_OWNER = "SHIP_OWNER",
  ADMIN = "ADMIN"
}