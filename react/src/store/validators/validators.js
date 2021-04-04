export const validateEmail = (email) => {
    const recEmail = email.trim();
    const pattern = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
    return pattern.test(recEmail);
}

export const validateBillCost = (billCost) => {
    const cost = billCost.trim();
    const pattern = /^\d+$/;
    if(cost === ""){
        return false;
    }
    return pattern.test(cost);
}

export const validateMobileNumber = (mobileNumber) => {
    const number = mobileNumber.trim();
    const pattern = /^\d+$/;
    return pattern.test(number) && number.length>=10 && number.length<=12;
}

export const validateBillNumber = (billNumber) => {
    const number = billNumber.trim();
    const pattern = /^\d+$/;
    return pattern.test(number);
}