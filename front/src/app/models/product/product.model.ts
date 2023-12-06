import { Body } from "../body/body.model";
import { House } from "../house/house.model";
import { TypeProduct } from "../type/type.model";

export class Product {
    productId: number;
    productName: string;
    productDescription: string;
    productUnitPrice: number;
    productStock: number;
    productLink: string;
    house: House;
    productTypes: TypeProduct[];
    bodyParts: Body[];
    productImageUrl!: string
    visible: boolean;
    constructor(id: number, name: string, description: string, price: number, stock: number, link: string, house: House, type: TypeProduct[], body: Body[], isVisible: boolean) {
        this.productId = id;
        this.productName = name;
        this.productDescription = description;
        this.productUnitPrice = price;
        this.productStock = stock;
        this.house = house;
        this.productLink = link;
        this.productTypes = type;
        this.bodyParts = body;
        this.visible = isVisible;
    }
}
