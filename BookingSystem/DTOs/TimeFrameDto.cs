using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace BookingSystem.DTOs
{
    public class TimeFrameDto
    {
        [DataType(DataType.DateTime, ErrorMessage = "Plese enter a valid time")]
        public DateTime StartTime { get; set; }
        [DataType(DataType.DateTime, ErrorMessage = "Plese enter a valid time")]
        public DateTime EndTime { get; set; }
    }
}
