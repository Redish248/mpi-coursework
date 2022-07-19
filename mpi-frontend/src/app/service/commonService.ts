export class CommonService {

    static getMax(arr: any[], field: string): number {
        let max = 0
        arr.forEach(el => {
            if (el[field] > max) max = el[field]
        })
        return max
    }

    static getMax_2(arr: any[], field1: string, filed2: string): number {
        let max = 0
        arr.forEach(el => {
            if (el[field1][filed2] > max) max = el[field1][filed2]
        })
        return max
    }

    static getMin(arr: any[], field: string) {
        let min = Number.MAX_SAFE_INTEGER
        arr.forEach(el => {
            if (el[field] < min) min = el[field]
        })
        return min
    }

    static getMin_2(arr: any[], field1: string, field2: string) {
        let min = Number.MAX_SAFE_INTEGER
        arr.forEach(el => {
            if (el[field1][field2] < min) min = el[field1][field2]
        })
        return min
    }
}
