using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BookingSystem.Models
{
    public class Appointment
    {
        public Customer Customer { get; set; }
        public Seller Seller { get; set; }

        public TimeFrame TimeFrame { get; set; }

        public bool Accepted { get; set; }

    }
}
