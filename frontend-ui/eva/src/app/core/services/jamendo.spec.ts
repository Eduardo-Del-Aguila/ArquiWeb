import { TestBed } from '@angular/core/testing';

import { Jamendo } from './jamendo';

describe('Jamendo', () => {
  let service: Jamendo;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Jamendo);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
