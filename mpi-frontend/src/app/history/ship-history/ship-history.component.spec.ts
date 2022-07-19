import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ShipHistoryComponent} from './ship-history.component';

describe('ShipHistoryComponent', () => {
    let component: ShipHistoryComponent;
    let fixture: ComponentFixture<ShipHistoryComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ShipHistoryComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ShipHistoryComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
