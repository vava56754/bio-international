import { ProcurementState } from "src/app/enums/procrument-state.enum";
import { LineProcurement } from "./line-procurement.model";
import { User } from "../user/user.model";

export class Procurement {
    procurementId: number;
    procurementDate: Date;
    procurementState: ProcurementState;
    lineProcurementList: LineProcurement[];
    amount: number;
    userId: User;

    constructor(id: number, data: Date, state: ProcurementState, list: LineProcurement[], amount: number, userId: User) {
        this.procurementId = id;
        this.procurementDate = data;
        this.procurementState = state;
        this.lineProcurementList = list;
        this.amount = amount;
        this.userId = userId;
    }

}
