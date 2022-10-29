export default {
    getNumberFormatted(val: any) {
        return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
    }
}