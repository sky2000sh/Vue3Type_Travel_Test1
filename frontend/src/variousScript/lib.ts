export default {
    getNumberFormatted(val: { toString: () => string }) {
        return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
    }
}