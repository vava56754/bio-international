export class House {
     houseId: number;
     houseName: string;
     houseDescription: string;
     houseLink: string;
     houseFile: Blob;
     houseImageUrl!: string;

    constructor() {
        this.houseId = 0;
        this.houseName = 'name';
        this.houseDescription = 'description';
        this.houseLink = 'link';
        this.houseFile = new Blob;
    }

    getHouseId(): number {
        return this.houseId;
    }

    getHouseName(): string {
        return this.houseName;
    }

    getHouseDescription(): string {
        return this.houseDescription;
    }

    getHouseLink(): string {
        return this.houseLink;
    }

    getHouseFile(): Blob {
        return this.houseFile;
    }

    setHouseId(id: number){
        this.houseId = id;
    }

    setHouseName(name: string) {
        this.houseName = name;
    }

    setHouseDescription(description: string) {
        this.houseDescription = description;
    }

    setHouseFile(file: Blob) {
        this.houseFile = file;
    }

    setHouseImageUrl(url: string) {
        this.houseImageUrl = url;
    }
 
}
