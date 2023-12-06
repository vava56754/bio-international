import { Product } from "../product/product.model";

export class LineProcurement {
    lineId: number;
    lineUnitPrice: number;
    lineQuantity: number;
    product: Product;

    constructor(id: number, price: number, quantity: number, product: Product) {
        this.lineId = id;
        this.lineUnitPrice = price;
        this.lineQuantity = quantity;
        this.product = product;
    }
}
