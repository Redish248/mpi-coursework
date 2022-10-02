import { TestBed } from '@angular/core/testing';

import { FsbService } from './fsb.service';

describe('FsbService', () => {
  let service: FsbService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FsbService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
