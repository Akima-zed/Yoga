/// <reference types="jest" />
declare global {
  namespace jest {
    interface Matchers<R> {
      toHaveBeenCalledWith(...args: unknown[]): R;
      toHaveBeenCalled(): R;
      toBeTruthy(): R;
      toBeUndefined(): R;
      toEqual(expected: unknown): R;
    }
  }
}
export {};
