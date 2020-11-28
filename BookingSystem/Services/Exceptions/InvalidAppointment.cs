using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BookingSystem.Services.Exceptions
{
    public class InvalidAppointment :Exception
    {
        public InvalidAppointment(string message) : base(message) {  }
    }
}
