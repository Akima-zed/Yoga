declare global {
  namespace jest {
    interface Matchers<R> {
      toHaveBeenCalled(): R;
      toHaveBeenCalledWith(...args: any[]): R;
      toHaveBeenCalledTimes(times: number): R;
      toBeTruthy(): R;
      toBeFalsy(): R;
      toBeUndefined(): R;
      toBeDefined(): R;
      toEqual(expected: any): R;
      toStrictEqual(expected: any): R;
      toThrow(expected?: any): R;
    }
  }
}

export {};
