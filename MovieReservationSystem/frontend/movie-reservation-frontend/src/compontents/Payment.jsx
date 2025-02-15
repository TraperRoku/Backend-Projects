import React from 'react';
import { loadStripe } from '@stripe/stripe-js';
import { Elements } from '@stripe/react-stripe-js';
import CheckoutForm from './CheckoutForm';

const stripePromise = loadStripe('your_stripe_public_key');

const Payment = ({ reservationId }) => {
  return (
    <Elements stripe={stripePromise}>
      <CheckoutForm reservationId={reservationId} />
    </Elements>
  );
};

export default Payment;