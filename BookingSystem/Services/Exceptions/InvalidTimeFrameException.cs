using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BookingSystem.Services.Exceptions
{
    public class InvalidTimeFrameException : Exception
    {
        public InvalidTimeFrameException(string message) : base(message) { }
    }
}
