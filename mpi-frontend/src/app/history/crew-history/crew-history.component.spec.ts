import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CrewHistoryComponent} from './crew-history.component';

describe('CrewHistoryComponent', () => {
    let component: CrewHistoryComponent;
    let fixture: ComponentFixture<CrewHistoryComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [CrewHistoryComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(CrewHistoryComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
